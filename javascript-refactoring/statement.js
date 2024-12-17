const assert = require('assert');

module.exports = {
    statement,
    calculateAmount,
    calculateCredits
};

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

function statement(invoice, plays, playTypes) {
    const statementData = createStatementData(invoice, plays, playTypes);
    return renderStatement(statementData);
}

function calculateAmount(play, perf, playTypes) {
    
    const playParameters = playTypes[play.type];
    let thisAmount = playParameters.baseAmount;

    if (perf.audience > playParameters.audienceThreshold) {
        thisAmount += playParameters.extraBase + playParameters.extraPerAudience * (perf.audience - playParameters.audienceThreshold);
    }
    thisAmount += playParameters.perAudience * perf.audience;

    return thisAmount;
}

function calculateCredits(play, perf, playTypes) {
    const BASE_CREDITS_THRESHOLD = 30;
    const playParameters = playTypes[play.type];
    
    let credits = Math.max(perf.audience - BASE_CREDITS_THRESHOLD, 0);
    if (playParameters.hasCreditsBonus) {
        credits += Math.floor(perf.audience / playParameters.creditsBonusDivisor);
    }
    return credits;
}

function createFormatter() {
    return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD",
        minimumFractionDigits: 2,
    }).format;
}