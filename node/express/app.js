var express = require('express');
var path = require('path');
var logger = require('morgan');
var bodyParser = require('body-parser');
var config = require('config');
var mongoose = require('mongoose');

var todos = require('./routes/todos');

mongoose.connect(config.mongodb.url);
mongoose.Promise = global.Promise;

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));

var app = express();

if (process.env.NODE_ENV !== 'test') {
  app.use(logger('dev'));
}

// uncomment after placing your favicon in /public
app.use(bodyParser.json());
app.use(bodyParser.json({ type: 'application/json' }));
app.use(bodyParser.urlencoded({ extended: false }));

app.use('/todos', todos);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  switch (err.name) {
    case 'ValidationError':
      err.status = 422;
      break;
    default:
      // nop
   }

  // set error, only providing error in development
  const error = req.app.get('env') === 'development' ? err : {};
  const status = err.status || 500;

  // render the error
  res.status(status);
  res.send({
    message: err.message,
    status,
    error: error.errors,
  });
});

module.exports = app;
