class StatementFormatter:
    def __init__(self, statement_data):
        self.data = statement_data

    def format_amount(self, amount_cents):
        # Format cents to dollars with 2 decimals
        return f"${amount_cents / 100:,.2f}"

    def format(self):
        lines = f"Statement for {self.data['customer']}\n"

        for perf in self.data['performances']:
            name = perf['play']['name']
            amount = self.format_amount(perf['amount'])
            audience = perf['audience']
            lines += f"\t\t{name}: {amount} ({audience} seats)\n"

        total_amount = self.format_amount(self.data['totalAmount'])
        total_credits = self.data['totalCredits']

        lines += f"\tAmount owed is {total_amount}\n"
        lines += f"\tYou earned {total_credits} credits\n"

        return lines
