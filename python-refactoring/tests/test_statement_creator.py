import pytest

from src.core.statement_creator import StatementCreator


@pytest.mark.parametrize(
    "invoice, plays, expected",
    [
        # Tragedy + Comedy
        (
            {
                "customer": "BigCo",
                "performances": [
                    {"playID": "hamlet", "audience": 55},
                    {"playID": "as-like", "audience": 35},
                ],
            },
            {
                "hamlet": {"name": "Hamlet", "type": "tragedy"},
                "as-like": {"name": "As You Like It", "type": "comedy"},
            },
            {
                "Hamlet": {
                    "amount": 40000 + (25 * 1000),
                    "credits": max(55 - 30, 0),
                },
                "As You Like It": {
                    "amount": 30000 + 10000 + ((35 - 20) * 500) + (300 * 35),
                    "credits": max(35 - 30, 0) + (35 // 5),
                },
            },
        ),
        # Only Hamlet, exactly 30 audience (no bonus)
        (
            {
                "customer": "TragedyFan",
                "performances": [{"playID": "hamlet", "audience": 30}],
            },
            {
                "hamlet": {"name": "Hamlet", "type": "tragedy"},
            },
            {
                "Hamlet": {
                    "amount": 40000,
                    "credits": 0,
                },
            },
        ),
        # Only Comedy, audience > 20 (triggers bonus)
        (
            {
                "customer": "ComedyFan",
                "performances": [{"playID": "funny", "audience": 25}],
            },
            {
                "funny": {"name": "Funny Play", "type": "comedy"},
            },
            {
                "Funny Play": {
                    "amount": 30000 + 10000 + ((25 - 20) * 500) + (300 * 25),
                    "credits": max(25 - 30, 0) + (25 // 5),
                },
            },
        ),
        # Only Comedy, audience ≤ 20 (no bonus)
        (
            {
                "customer": "TinyCrowd",
                "performances": [{"playID": "chill-comedy", "audience": 20}],
            },
            {
                "chill-comedy": {"name": "Chill Comedy", "type": "comedy"},
            },
            {
                "Chill Comedy": {
                    "amount": 30000 + (300 * 20),
                    "credits": max(20 - 30, 0) + (20 // 5),
                },
            },
        ),
    ],
)
def test_statement_creator_various_valid_inputs(invoice, plays, expected):
    statement = StatementCreator(invoice, plays).create()

    total_amount = 0
    total_credits = 0

    for performance in statement["performances"]:
        play_name = performance["play"]["name"]
        assert performance["amount"] == expected[play_name]["amount"]
        assert performance["credits"] == expected[play_name]["credits"]
        total_amount += performance["amount"]
        total_credits += performance["credits"]

    assert statement["customer"] == invoice["customer"]
    assert statement["totalAmount"] == total_amount
    assert statement["totalCredits"] == total_credits
