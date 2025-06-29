from core.play_charge_calculator_factory import create_calculator

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
        for performance in self.invoice["performances"]:
            play = self.plays[performance["playID"]]
            play_charge_calculator = create_calculator(performance, play)

            amount = play_charge_calculator.amount()
            credits = play_charge_calculator.volume_credits()

            self.statement_data["performances"].append({
                "play": play,
                "audience": performance["audience"],
                "amount": amount,
                "credits": credits
            })

            total_amount += amount
            total_credits += credits

        self.statement_data["totalAmount"] = total_amount
        self.statement_data["totalCredits"] = total_credits

        return self.statement_data
