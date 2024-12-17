const fs = require('fs');
const { statement } = require('../statement');
const plays = require('../plays.json');
const invoices = require('../invoices.json');

// Generate statement for the first invoice and compare to the result.txt file
describe('Statement Generation', () => {
  test('should generate statement matching result.txt', () => {
    const result = statement(invoices[0], plays);

    const expected = fs.readFileSync('./result.txt', 'utf8')
      .replace(/\r\n/g, '\n')  // Convert CRLF to LF, due to different line endings in Windows vs Unix

    const normalizedResult = result
      .replace(/\r\n/g, '\n')  // Convert CRLF to LF

    expect(normalizedResult).toBe(expected);
  });
});