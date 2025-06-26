from calculators.tragedy_calculator import TragedyCalculator
from calculators.comedy_calculator import ComedyCalculator


def create_calculator(performance, play):

    if play["type"] == "tragedy":
        return TragedyCalculator(performance, play)
    elif play["type"] == "comedy":
        return ComedyCalculator(performance, play)
    else:
        raise ValueError(f"Unknown play type: {play['type']}")
