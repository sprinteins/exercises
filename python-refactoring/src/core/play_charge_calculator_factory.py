from enum import Enum

try:
    from ..calculators.tragedy_calculator import TragedyCalculator
    from ..calculators.comedy_calculator import ComedyCalculator
    from ..errors import ErrorMessages
except ImportError:
    import sys
    from pathlib import Path
    sys.path.insert(0, str(Path(__file__).parent.parent))
    from calculators.tragedy_calculator import TragedyCalculator
    from calculators.comedy_calculator import ComedyCalculator
    from errors import ErrorMessages

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
