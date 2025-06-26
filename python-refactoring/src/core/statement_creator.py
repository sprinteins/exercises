from .calculator_factory import create_calculator

class StatementCreator:
    def __init__(self, invoice, plays):
        self.invoice = invoice
        self.plays = plays
        self.statement_data = {
            "customer": invoice["customer"],
            "performances": [],
            "totalAmount": 0,
            "totalCredits": 0
        }

    def create(self):
        total_amount = 0
        total_credits = 0
        for perf in self.invoice["performances"]:
            play = self.plays[perf["playID"]]
            calculator = create_calculator(perf, play)

            amount = calculator.amount()
            credits = calculator.volume_credits()

            self.statement_data["performances"].append({
                "play": play,
                "audience": perf["audience"],
                "amount": amount,
                "credits": credits
            })

            total_amount += amount
            total_credits += credits

        self.statement_data["totalAmount"] = total_amount
        self.statement_data["totalCredits"] = total_credits

        return self.statement_data
