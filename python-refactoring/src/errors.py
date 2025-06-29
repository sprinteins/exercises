from enum import Enum, unique


@unique
class ErrorMessages(Enum):
    MUST_IMPLEMENT_AMOUNT = "Subclass must implement amount"
    UNKNOWN_PLAY_TYPE = "Unknown play type: {play_type}"
    FILE_NOT_FOUND = "File not found: {filename}"
    JSON_DECODE_ERROR = "JSON decoding error in file: {msg}"
    MISSING_KEY = "Missing key: {key}"
    VALUE_ERROR = "Value error: {error}"
    NOT_IMPLEMENTED = "Not implemented: {error}"
    INVALID_CUSTOMER = "Invalid or missing 'customer'"
    INVALID_PERFORMANCES = "Invalid or missing 'performances'"
    UNEXPECTED_ERROR = "Unexpected error: {error}"
