from .base_calculator import PerformanceCalculator

class TragedyCalculator(PerformanceCalculator):
    def amount(self):
        result = 40000
        if self.performance["audience"] > 30:
            result += 1000 * (self.performance["audience"] - 30)
        return result