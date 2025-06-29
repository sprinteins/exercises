from errors import ErrorMessages

class PlayChargeCalculator:
    def __init__(self, performance, play):
        self.performance = performance
        self.play = play

    def amount(self):
        raise NotImplementedError(ErrorMessages.MUST_IMPLEMENT_AMOUNT.value)

    def volume_credits(self):
        return max(self.performance["audience"] - 30, 0)