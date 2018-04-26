'use strict';

var _asyncGenerator = function () { function AwaitValue(value) { this.value = value; } function AsyncGenerator(gen) { var front, back; function send(key, arg) { return new Promise(function (resolve, reject) { var request = { key: key, arg: arg, resolve: resolve, reject: reject, next: null }; if (back) { back = back.next = request; } else { front = back = request; resume(key, arg); } }); } function resume(key, arg) { try { var result = gen[key](arg); var value = result.value; if (value instanceof AwaitValue) { Promise.resolve(value.value).then(function (arg) { resume("next", arg); }, function (arg) { resume("throw", arg); }); } else { settle(result.done ? "return" : "normal", result.value); } } catch (err) { settle("throw", err); } } function settle(type, value) { switch (type) { case "return": front.resolve({ value: value, done: true }); break; case "throw": front.reject(value); break; default: front.resolve({ value: value, done: false }); break; } front = front.next; if (front) { resume(front.key, front.arg); } else { back = null; } } this._invoke = send; if (typeof gen.return !== "function") { this.return = undefined; } } if (typeof Symbol === "function" && Symbol.asyncIterator) { AsyncGenerator.prototype[Symbol.asyncIterator] = function () { return this; }; } AsyncGenerator.prototype.next = function (arg) { return this._invoke("next", arg); }; AsyncGenerator.prototype.throw = function (arg) { return this._invoke("throw", arg); }; AsyncGenerator.prototype.return = function (arg) { return this._invoke("return", arg); }; return { wrap: function wrap(fn) { return function () { return new AsyncGenerator(fn.apply(this, arguments)); }; }, await: function _await(value) { return new AwaitValue(value); } }; }(); //import fetch from 'node-fetch';


var streamAsyncIterator = function () {
  var _ref = _asyncGenerator.wrap( /*#__PURE__*/regeneratorRuntime.mark(function _callee(response) {
    var reader, _ref2, done, value;

    return regeneratorRuntime.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            reader = response.body.getReader();
            _context.prev = 1;

          case 2:
            if (!true) {
              _context.next = 14;
              break;
            }

            _context.next = 5;
            return _asyncGenerator.await(reader.read());

          case 5:
            _ref2 = _context.sent;
            done = _ref2.done;
            value = _ref2.value;

            if (!done) {
              _context.next = 10;
              break;
            }

            return _context.abrupt('return');

          case 10:
            _context.next = 12;
            return value;

          case 12:
            _context.next = 2;
            break;

          case 14:
            _context.prev = 14;

            reader.releaseLock();
            return _context.finish(14);

          case 17:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this, [[1,, 14, 17]]);
  }));

  return function streamAsyncIterator(_x) {
    return _ref.apply(this, arguments);
  };
}();

var example = function () {
  var _ref3 = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee2() {
    var find, findCode, response, bytes, _iteratorNormalCompletion, _didIteratorError, _iteratorError, _iterator, _step, _value, chunk, index;

    return regeneratorRuntime.wrap(function _callee2$(_context2) {
      while (1) {
        switch (_context2.prev = _context2.next) {
          case 0:
            find = 'J';
            findCode = find.codePointAt(0);
            _context2.next = 4;
            return fetch('https://html.spec.whatwg.org');

          case 4:
            response = _context2.sent;
            bytes = 0;
            _iteratorNormalCompletion = true;
            _didIteratorError = false;
            _iteratorError = undefined;
            _context2.prev = 9;
            _iterator = _asyncIterator(streamAsyncIterator(response.body));

          case 11:
            _context2.next = 13;
            return _iterator.next();

          case 13:
            _step = _context2.sent;
            _iteratorNormalCompletion = _step.done;
            _context2.next = 17;
            return _step.value;

          case 17:
            _value = _context2.sent;

            if (_iteratorNormalCompletion) {
              _context2.next = 29;
              break;
            }

            chunk = _value;
            index = chunk.indexOf(findCode);

            if (!(index != -1)) {
              _context2.next = 25;
              break;
            }

            bytes += index;
            console.log('Found ' + find + ' at byte ' + bytes + '.');
            return _context2.abrupt('break', 29);

          case 25:

            bytes += chunk.length;

          case 26:
            _iteratorNormalCompletion = true;
            _context2.next = 11;
            break;

          case 29:
            _context2.next = 35;
            break;

          case 31:
            _context2.prev = 31;
            _context2.t0 = _context2['catch'](9);
            _didIteratorError = true;
            _iteratorError = _context2.t0;

          case 35:
            _context2.prev = 35;
            _context2.prev = 36;

            if (!(!_iteratorNormalCompletion && _iterator.return)) {
              _context2.next = 40;
              break;
            }

            _context2.next = 40;
            return _iterator.return();

          case 40:
            _context2.prev = 40;

            if (!_didIteratorError) {
              _context2.next = 43;
              break;
            }

            throw _iteratorError;

          case 43:
            return _context2.finish(40);

          case 44:
            return _context2.finish(35);

          case 45:

            response.body.cancel();

          case 46:
          case 'end':
            return _context2.stop();
        }
      }
    }, _callee2, this, [[9, 31, 35, 45], [36,, 40, 44]]);
  }));

  return function example() {
    return _ref3.apply(this, arguments);
  };
}();

require('whatwg-fetch');

function _asyncIterator(iterable) { if (typeof Symbol === "function") { if (Symbol.asyncIterator) { var method = iterable[Symbol.asyncIterator]; if (method != null) return method.call(iterable); } if (Symbol.iterator) { return iterable[Symbol.iterator](); } } throw new TypeError("Object is not async iterable"); }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { return Promise.resolve(value).then(function (value) { step("next", value); }, function (err) { step("throw", err); }); } } return step("next"); }); }; }

example();
