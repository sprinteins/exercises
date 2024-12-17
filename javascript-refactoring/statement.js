const assert = require('assert');

module.exports = {
    statement,
    calculateAmount,
    calculateCredits
};

/**
 * @typedef {Object} Play
 * @property {string} name
 * @property {string} type
 */

/**
 * @typedef {Object} Performance
 * @property {string} playID
 * @property {number} audience
 */

/**
 * @typedef {Object} Invoice
 * @property {string} customer
 * @property {Performance[]} performances
 */

/**
 * @typedef {Object} PlayTypeParameters
 * @property {number} baseAmount
 * @property {number} audienceThreshold
 * @property {number} extraBase
 * @property {number} extraPerAudience
 * @property {number} perAudience
 * @property {boolean} hasCreditsBonus
 * @property {number} creditsBonusDivisor
 */

/**
 * @typedef {Object.<string, PlayTypeParameters>} PlayTypes
 */

/**
 * @typedef {Object} StatementPerformance
 * @property {Play} play
 * @property {number} amount
 * @property {number} audience
 */

/**
 * @typedef {Object} StatementData
 * @property {string} customer
 * @property {StatementPerformance[]} performances
 * @property {number} totalAmount
 * @property {number} totalVolumeCredits
 */


/**
 * Creates statement data from invoice and play information.
 * @param {Invoice} invoice
 * @param {Object.<string, Play>} plays
 * @param {PlayTypes} playTypes
 * @returns {StatementData}
 */
function createStatementData(invoice, plays, playTypes) {
    let totalAmount = 0;
    let volumeCredits = 0;
    const statementData = {
        customer: invoice.customer,
        performances: [],
        totalAmount: 0,
        totalVolumeCredits: 0
    };

    for (let perf of invoice.performances) {
        const play = plays[perf.playID];
        assert(playTypes[play.type], `unknown type: ${play.type}`);
        
        const thisAmount = calculateAmount(play, perf, playTypes);
        const credits = calculateCredits(play, perf, playTypes);

        statementData.performances.push({
            play: play,
            amount: thisAmount,
            audience: perf.audience
        });

        totalAmount += thisAmount;
        volumeCredits += credits;
    }
    
    statementData.totalAmount = totalAmount;
    statementData.totalVolumeCredits = volumeCredits;
    return statementData;
}

/**
 * Renders the statement data into a formatted string.
 * @param {StatementData} statementData
 * @returns {string}
 */
function renderStatement(statementData) {
    const format = createFormatter();
    let result = `Statement for ${statementData.customer}\n`;

    for (let perf of statementData.performances) {
        result += `    ${perf.play.name}: ${format(perf.amount / 100)} (${perf.audience} seats)\n`;
    }

    result += `  Amount owed is ${format(statementData.totalAmount / 100)}\n`;
    result += `  You earned ${statementData.totalVolumeCredits} credits`;
    return result;
}

/**
 * Generates a formatted statement for an invoice.
 * @param {Invoice} invoice
 * @param {Object.<string, Play>} plays
 * @param {PlayTypes} playTypes
 * @returns {string}
 */
function statement(invoice, plays, playTypes) {
    try {
        const statementData = createStatementData(invoice, plays, playTypes);
        return renderStatement(statementData);
    } catch (error) {
      console.error("Error generating statement:", error.message);
      return "Statement could not be generated due to an error."
    }
}
/**
 * Calculates the amount for a single performance.
 * @param {Play} play
 * @param {Performance} perf
 * @param {PlayTypes} playTypes
 * @returns {number}
 */
function calculateAmount(play, perf, playTypes) {
    
    const playParameters = playTypes[play.type];
    let thisAmount = playParameters.baseAmount;

    if (perf.audience > playParameters.audienceThreshold) {
        thisAmount += playParameters.extraBase + playParameters.extraPerAudience * (perf.audience - playParameters.audienceThreshold);
    }
    thisAmount += playParameters.perAudience * perf.audience;

    return thisAmount;
}

/**
 * Calculates the credits for a single performance.
 * @param {Play} play
 * @param {Performance} perf
 * @param {PlayTypes} playTypes
 * @returns {number}
 */
function calculateCredits(play, perf, playTypes) {
    const BASE_CREDITS_THRESHOLD = 30;
    const playParameters = playTypes[play.type];
    
    let credits = Math.max(perf.audience - BASE_CREDITS_THRESHOLD, 0);
    if (playParameters.hasCreditsBonus) {
        credits += Math.floor(perf.audience / playParameters.creditsBonusDivisor);
    }
    return credits;
}

/**
 * Creates a formatter for currency.
 * @returns {(number) => string}
 */
function createFormatter() {
    return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD",
        minimumFractionDigits: 2,
    }).format;
}