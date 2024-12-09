const formatCurrency = value => {
    /**
     * A function, which receive an amount of money and format that in USD 
     * @param {int}   value       A json object, which contains the sales detail of each play
     *
     * @return {string} Return the styled value in the USD format.
     */
    return new Intl.NumberFormat("en-US",
        {
            style: "currency", currency: "USD",
            minimumFractionDigits: 2
        }).format(value / 100);
    }

const statement = (invoice, plays) => {
    /**
     * A function, which create a Sale report for an invoice 
     * @param {object}   invoice       A json object, which contains the sales detail of each play
     * @param {object}   plays         A json object, which contains a mapping(or config) for each play
     *
     * @return {string} Return the generated report as a string, for the invoice.
     */
    let totalSaleAmount = 0;
    let volumeCredits = 0;
    let salseResult = '';

    for (const performance of invoice.performances) {
        if(!(performance.playID in plays)) throw new Error(`unknown playID for: ${performance.playID}`)
        const play = plays[performance.playID];
        const [ volumeCredit, saleAmount ] = calculator(performance, play);
        // print line for this order
        salseResult += `  ${play.name}: ${formatCurrency(saleAmount)} (${performance.audience} seats)\n`;
        totalSaleAmount += saleAmount;
        volumeCredits += volumeCredit;
    }
    const saleReport = `Statement for ${invoice.customer}:\n\n ${salseResult} 
    Amount owed is: ${formatCurrency(totalSaleAmount)}\n You earned ${volumeCredits} credits\n`;
    return saleReport;
}

const calculator = (performance, play) => {
    /**
     * A function, which calculate the total sale amount of the given play, 
     * and the Credit which saler receive for it.
     * @param {object}   performance     Performance sale detail
     * @param {object}   play            Extra information about how to calculate the given play inside the performance
     *
     * @return {int, int} Return the calculated credit and sale amount.
     */
    let saleAmount = play.amount.total;
    if (performance.audience > play.audienceLimit) {
        saleAmount += play.amount.constant + (play.amount.multiple1 * (performance.audience - play.audienceLimit));
    }
    saleAmount += play.amount.multiple2 * performance.audience;

    // add volume credits if the limit is reached
    let volumeCredit = Math.max(performance.audience - play.audienceVolumeCredit, 0);
    // add extra credit if the play has the bonus
    if (play.audienceBonus) volumeCredit += Math.floor(performance.audience / play.audienceBonus);
    return [volumeCredit, saleAmount];
}

export default statement;
