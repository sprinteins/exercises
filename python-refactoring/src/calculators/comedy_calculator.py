try:
    from .play_charge_calculator import PlayChargeCalculator
except ImportError:
    from play_charge_calculator import PlayChargeCalculator

class ComedyCalculator(PlayChargeCalculator):
    def amount(self):
        audience = self.performance["audience"]
        result = 30000
        if audience > 20:
            result += 10000 + 500 * (audience - 20)
        result += 300 * audience
        return result

    def volume_credits(self):
        return super().volume_credits() + self.performance["audience"] // 5