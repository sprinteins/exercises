import json
from core.statement_generator import StatementCreator
from core.statement_formatter import StatementFormatter

def load_json_file(filename):
    with open(filename) as f:
        return json.load(f)

try:
    plays = load_json_file("data/plays.json")
    invoices = load_json_file("data/invoices.json")

    creator = StatementCreator(invoices[0], plays)
    statement_data = creator.create()

    formatter = StatementFormatter(statement_data)
    statement_text = formatter.format()

    output_file = "outputs/output_result.txt"
    with open(output_file, "w") as f:
        f.write(statement_text)

    print(f"Statement written to {output_file}")


except FileNotFoundError as e:
    print(f"File not found: {e.filename}")
except json.JSONDecodeError as e:
    print(f"JSON decoding error in file: {e.msg}")
except KeyError as e:
    print(f"Missing key: {e}")
except ValueError as e:
    print(f"Value error: {e}")
except NotImplementedError as e:
    print(f"Not implemented: {e}")
except Exception as e:
    print(f"Unexpected error: {e}")
