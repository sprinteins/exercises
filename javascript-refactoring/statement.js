module.exports = {
    statement,
};

function statement(invoice, plays) {
    const statementData = getStatementData(invoice, plays);
    return renderPlainText(statementData);
}

function getStatementData(invoice, plays) {
    const statementData = {
        customer: invoice.customer,
        performances: invoice.performances.map(getPerformance),
    };

    statementData.totalAmount = calculateTotalAmount(statementData.performances);
    statementData.totalVolumeCredits = calculateTotalVolumeCredits(statementData.performances);

    return statementData;

    function getPerformance(performance) {
        const play = plays[performance.playID];
        return {
            ...performance,
            play,
            amount: calculatePerformanceAmount(performance, play),
            volumeCredit: calculateVolumeCredits(performance, play)
        }
    }
}

function calculateTotalAmount(performances) {
    return performances.reduce((total, perf) => total + perf.amount, 0);
}

function calculateTotalVolumeCredits(performances) {
    return performances.reduce((total, perf) => total + perf.volumeCredit, 0);
}

function calculatePerformanceAmount(performance, play) {
    let amount = 0;

    switch (play.type) {
        case "tragedy":
            amount = 40000;
            if (performance.audience > 30) {
                amount += 1000 * (performance.audience - 30);
            }
            break;
        case "comedy":
            amount = 30000;
            if (performance.audience > 20) {
                amount += 10000 + 500 * (performance.audience - 20);
            }
            amount += 300 * performance.audience;
            break;
        default:
            throw new Error(`unknown type: ${play.type}`);
    }

    return amount;
}

function calculateVolumeCredits(performance, play) {
    let volumeCredits = Math.max(performance.audience - 30, 0);

    if (play.type === "comedy") {
        volumeCredits += Math.floor(performance.audience / 5);
    }

    return volumeCredits;
}

function renderPlainText(data) {
    let result = `Statement for ${data.customer}\n`;

    for (let perf of data.performances) {
        result += `  ${perf.play.name}: ${formatCurrency(perf.amount)} (${perf.audience} seats)\n`;
    }

    result += `Amount owed is ${formatCurrency(data.totalAmount)}\n`;
    result += `You earned ${data.totalVolumeCredits} credits\n`;

    return result;
}

function formatCurrency(amount) {
    return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD",
        minimumFractionDigits: 2,
    }).format(amount / 100);
}
