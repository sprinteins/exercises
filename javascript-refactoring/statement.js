module.exports = {
    statement,
}


const SPLIT_REGEX = /-\s/

const PLAY_TYPES = {
    tragedy:  "tragedy"
    , comedy: "comedy"
};


class PlayTypeError extends Error {
    constructor(message='Invalid play type') {
        super(message);
        this.name = "PlayTypeError";
    }
}


function decomposeStringIntoWords(playId) {
    return playId.split(SPLIT_REGEX);
}


function hasRightJointSimilarity(arrA, arrB) { // check if all elements of b are present in a
    const containsAll = arrB.every(el => {
        return arrA.includes(el);
    });
    return containsAll;
}


function getPlayById(plays, playId) {
    playId = decomposeStringIntoWords(playId);
    const playKey = Object.keys(plays).find((key) => {
                                            return hasRightJointSimilarity(
                                                    decomposeStringIntoWords(key)
                                                    , playId
                                                )// check if key and playId are similar
                                            });
    return plays[playKey];
}


function addPenaltyForLargeAudience(audience, thresholdNumber, penaltySumForEach, additionalPenalty=0) {
    if (audience <= thresholdNumber) {
        return 0; // penalty is 0 if audience does not exceed threshold
    }

    const numberOfExceedingMembers = (audience - thresholdNumber); // number of audience members that exceed the threshold
    const totalPenaltyForExceedingMembers = penaltySumForEach * numberOfExceedingMembers; // add penalty for every person in the audience that is over the threshold number
    return additionalPenalty + totalPenaltyForExceedingMembers;
}


function additionalExpensesForMembers(numberOfPeople, expensesForEach) {
    return expensesForEach * numberOfPeople;
}


const formatCurrency = new Intl.NumberFormat("en-US",
{
    style: "currency", currency: "USD",
    minimumFractionDigits: 2
}).format;      // create a currency formatter. Format numbers to a pretty format for USD (example: 10 = "$10,00")


function toCoins(banknotes, coins=0) {
    return banknotes * 100 + coins;
}


function toBanknotes(coins) {
    return coins / 100;
}


function formatLineForPlay(playName, amount, audience) {
    return `  ${playName}: ${formatCurrency(toBanknotes(amount))} (${audience} seats)\n`
}


function getStatementForPlay(playType, audience) {
    let thisAmount = 0;
    let volumeCredits = 0;
    
    switch (playType) {
        case PLAY_TYPES.tragedy:
            thisAmount = toCoins(400); // sums are stored in coins, so 400_00 is 400 dollars
            thisAmount += addPenaltyForLargeAudience(audience, 30, toCoins(10)); // if exceeds 30 members then pay 1_000 for each
            break;
        case PLAY_TYPES.comedy:
            thisAmount = toCoins(300);
            thisAmount += addPenaltyForLargeAudience(audience, 20, toCoins(5), toCoins(100));
            thisAmount += additionalExpensesForMembers(toCoins(3), audience); // additional expenses for each member
            // add extra credit for every 5 comedy attendees
            volumeCredits += Math.floor(audience / 5);
            break;
        default:
            throw new PlayTypeError(`Unknown play type: "${playType}"`);
    }

    const thresholdForGivingCredits = 30; // give credits for audiences larger than this threshold. 1 credit for every person that exceeds the threshold
    // add volume credits
    volumeCredits += Math.max(audience - thresholdForGivingCredits, 0); //! This seems strange that the number volume credits earned is dependant on the max audience for tragedy, but maybe this is intended
                                                              //! But maybe we just in general get credits for every play that gathered audience more than 30 people 

    return {
        thisAmount
        , volumeCredits
    }
}


function statement(invoice, plays) { //! better name generateStatement
    let totalAmount = 0;
    let volumeCredits = 0;

    let result = `Statement for ${invoice.customer}\n`;

    for (let perf of invoice.performances) {
        const play = getPlayById(plays, perf.playID);
        let thisAmount = 0;                         //TODO: possibly rename to 'currentAmount' or 'amountForPlay'

        const statementForPlay = getStatementForPlay(play.type, perf.audience)
        thisAmount    += statementForPlay.thisAmount
        volumeCredits += statementForPlay.volumeCredits
 
        totalAmount += thisAmount;
        // format line for this order
        result += formatLineForPlay(play.name, thisAmount, perf.audience);
    }

    result += `Amount owed is ${formatCurrency(toBanknotes(totalAmount))}\n`; // convert coins to banknotes
    result += `You earned ${volumeCredits} credits\n`;
    return result;
}



module.exports = {
    statement
    , PlayTypeError
};