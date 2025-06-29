from src.calculators.play_charge_calculator import PlayChargeCalculator


class TragedyCalculator(PlayChargeCalculator):
    def amount(self):
        result = 40000
        if self.performance["audience"] > 30:
            result += 1000 * (self.performance["audience"] - 30)
        return result
