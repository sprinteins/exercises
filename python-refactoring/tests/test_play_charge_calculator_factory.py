import pytest

from src.calculators.comedy_calculator import ComedyCalculator
from src.calculators.tragedy_calculator import TragedyCalculator
from src.core.play_charge_calculator_factory import PlayType, create_calculator
from src.errors import ErrorMessages


def test_create_calculator_tragedy():
    performance = {"audience": 40}
    play = {"type": PlayType.TRAGEDY.value}
    calculator = create_calculator(performance, play)
    assert isinstance(calculator, TragedyCalculator)


def test_create_calculator_comedy():
    performance = {"audience": 40}
    play = {"type": PlayType.COMEDY.value}
    calculator = create_calculator(performance, play)
    assert isinstance(calculator, ComedyCalculator)


def test_create_calculator_with_invalid_play_type():
    performance = {"audience": 40}
    play = {"type": "musical"}

    with pytest.raises(ValueError) as excinfo:
        create_calculator(performance, play)

    assert str(excinfo.value) == ErrorMessages.MUST_IMPLEMENT_AMOUNT.value
