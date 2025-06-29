from enum import Enum

from src.calculators.tragedy_calculator import TragedyCalculator
from src.calculators.comedy_calculator import ComedyCalculator
from src.errors import ErrorMessages

class PlayType(Enum):
    TRAGEDY = "tragedy"
    COMEDY = "comedy"


def create_calculator(performance, play):
    play_type = play.get("type")

    if play_type == PlayType.TRAGEDY.value:
        return TragedyCalculator(performance, play)
    elif play_type == PlayType.COMEDY.value:
        return ComedyCalculator(performance, play)
    else:
        raise ValueError(ErrorMessages.MUST_IMPLEMENT_AMOUNT.value)
