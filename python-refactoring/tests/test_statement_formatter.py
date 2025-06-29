import pytest

from errors import ErrorMessages
from src.core.statement_formatter import StatementFormatter


def test_format_output_structure():
    data = {
        "customer": "BigCo",
        "performances": [
            {
                "play": {"name": "Hamlet"},
                "audience": 55,
                "amount": 65000,
            },
            {
                "play": {"name": "As You Like It"},
                "audience": 35,
                "amount": 58000,
            },
        ],
        "totalAmount": 123000,
        "totalCredits": 47,
    }

    formatter = StatementFormatter(data)
    output = formatter.format()

    assert "Statement for BigCo" in output
    assert "Hamlet: $650.00 (55 seats)" in output
    assert "As You Like It: $580.00 (35 seats)" in output
    assert "Amount owed is $1,230.00" in output
    assert "You earned 47 credits" in output


def test_format_with_no_performances():
    data = {
        "customer": "NoOne",
        "performances": [],
        "totalAmount": 0,
        "totalCredits": 0,
    }

    formatter = StatementFormatter(data)
    output = formatter.format()

    assert "Statement for NoOne" in output
    assert "Amount owed is $0.00" in output
    assert "You earned 0 credits" in output


def test_statement_formatter_invalid_performances():
    invalid_data = {"customer": "Hi", "performances": "not-a-list"}

    try:
        StatementFormatter(invalid_data).format()
        pytest.fail("Expected ValueError but none was raised")
    except ValueError as e:
        assert str(e) == ErrorMessages.INVALID_PERFORMANCES.value


def test_statement_formatter_invalid_customer():
    invalid_data = {
        "customer": 123,
        "performances": [{"play": {"name": "Hamlet"}, "audience": 55, "amount": 65000}],
    }

    try:
        StatementFormatter(invalid_data).format()
        pytest.fail("Expected ValueError but none was raised")
    except ValueError as e:
        assert str(e) == ErrorMessages.INVALID_CUSTOMER.value
