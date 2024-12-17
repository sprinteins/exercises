const fs = require('fs');
const { statement, calculateAmount, calculateCredits } = require('../statement');
const plays = require('../plays.json');
const invoices = require('../invoices.json');
const playTypes = require('../playTypes.json');

// Generate statement for the first invoice and compare to the result.txt file
describe('Statement Generation', () => {
  test('should generate statement matching result.txt', () => {
    const result = statement(invoices[0], plays, playTypes);

    const expected = fs.readFileSync('./result.txt', 'utf8')
      .replace(/\r\n/g, '\n')  // Convert CRLF to LF, due to different line endings in Windows vs Unix

    const normalizedResult = result
      .replace(/\r\n/g, '\n')  // Convert CRLF to LF

    expect(normalizedResult).toBe(expected);
  });
});
// Test the calculationAmount and calculateCredits function
describe('calculateAmount', () => {
  test('calculates correct amount for Hamlet performance', () => {
      const performance = invoices[0].performances[0]; // Hamlet with 55 audience
      const play = plays[performance.playID];
      const expectedAmount = 65000; // from results.txt
      expect(calculateAmount(play, performance, playTypes)).toBe(expectedAmount);
  });
});

// Test the calculateCredits function
describe('calculateCredits', () => {
  test('calculates correct credits for Hamlet performance', () => {
      const performance = invoices[0].performances[0]; // Hamlet with 55 audience
      const play = plays[performance.playID];
      const expectedCredits = 25; // 55 - 30 = 25
      expect(calculateCredits(play, performance, playTypes)).toBe(expectedCredits);
  });
});
