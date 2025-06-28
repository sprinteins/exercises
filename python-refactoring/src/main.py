import json
from core.statement_generator import StatementCreator
from core.statement_formatter import StatementFormatter
from errors import ErrorMessages

def load_json_file(filename):
    with open(filename) as f:
        return json.load(f)

def write_text_file(output_file, text):
    with open(output_file, "w") as f:
        f.write(text)

try:
    plays = load_json_file("data/plays.json")
    invoices = load_json_file("data/invoices.json")

    creator = StatementCreator(invoices[0], plays)
    statement_data = creator.create()

    formatter = StatementFormatter(statement_data)
    statement_text = formatter.format()

    output_file = "outputs/output_result.txt"
    write_text_file(output_file, statement_text)

    print(f"Statement written to {output_file}")


except FileNotFoundError as e:
    print(ErrorMessages.FILE_NOT_FOUND.value.format(filename=e.filename))
except json.JSONDecodeError as e:
    print(ErrorMessages.JSON_DECODE_ERROR.value.format(msg=e.msg))
except KeyError as e:
    print(ErrorMessages.MISSING_KEY.value.format(key=e))
except ValueError as e:
    print(ErrorMessages.VALUE_ERROR.value.format(error=e))
except NotImplementedError as e:
    print(ErrorMessages.NOT_IMPLEMENTED.value.format(error=e))
except Exception as e:
    print(ErrorMessages.UNEXPECTED_ERROR.value.format(error=e))