import pytest

from src.core.statement_creator import StatementCreator


def test_statement_creator_exact_amounts_and_credits():
    plays = {
        "hamlet": {"name": "Hamlet", "type": "tragedy"},
        "as-like": {"name": "As You Like It", "type": "comedy"},
    }

    invoice = {
        "customer": "BigCo",
        "performances": [
            {"playID": "hamlet", "audience": 55},
            {"playID": "as-like", "audience": 35},
        ],
    }

    creator = StatementCreator(invoice, plays)
    statement = creator.create()

    # Hemlet
    expected_hamlet_amount = 40000 + (25 * 1000)
    expected_hamlet_credits = max(55 - 30, 0)

    # As You Like It
    expected_comedy_amount = 30000 + 10000 + ((35 - 20) * 500) + (300 * 35)
    expected_comedy_credits = max(35 - 30, 0) + (35 // 5)

    expected_total_amount = expected_hamlet_amount + expected_comedy_amount
    expected_total_credits = expected_hamlet_credits + expected_comedy_credits

    hamlet_perf = statement["performances"][0]
    comedy_perf = statement["performances"][1]

    assert statement["customer"] == "BigCo"

    assert hamlet_perf["play"]["name"] == "Hamlet"
    assert hamlet_perf["amount"] == expected_hamlet_amount
    assert hamlet_perf["credits"] == expected_hamlet_credits

    assert comedy_perf["play"]["name"] == "As You Like It"
    assert comedy_perf["amount"] == expected_comedy_amount
    assert comedy_perf["credits"] == expected_comedy_credits

    assert statement["totalAmount"] == expected_total_amount
    assert statement["totalCredits"] == expected_total_credits


def test_statement_creator_missing_key():
    plays = {"hamlet": {"type": "tragedy"}}
    invoice = {"performances": []}  # missing "customer"

    with pytest.raises(KeyError) as excinfo:
        StatementCreator(invoice, plays).create()

    assert str(excinfo.value).strip("'") == "customer"
