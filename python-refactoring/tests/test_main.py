import json
from pathlib import Path
from src.core.statement_creator import StatementCreator
from src.core.statement_formatter import StatementFormatter

def load_json_file(filename):
    base_path = Path(__file__).resolve().parent.parent
    data_path = base_path / "data" / filename
    with open(data_path) as f:
        return json.load(f)

def test_generate_statement_contains_key_lines():
    plays = load_json_file("plays.json")
    invoices = load_json_file("invoices.json")

    creator = StatementCreator(invoices[0], plays)
    statement_data = creator.create()

    formatter = StatementFormatter(statement_data)
    statement = formatter.format()

    assert "Statement for BigCo" in statement
    assert "Hamlet" in statement
    assert "As You Like It" in statement
    assert "Amount owed is $" in statement
    assert "You earned" in statement
