const mongoose = require('mongoose');
const Todo = require('../models/todo');
const express = require('express');

const router = express.Router();

const notFound = () => {
  const err = new Error('Not Found');
  err.status = 404;
  return err;
}

const handleError = next => err => {
  switch (err.name) {
    case 'ValidationError':
      err.status = 422;
      break;
    default:
      // nop
  }
  return next(err);
};

/**
 * GET /todos
 */
router.get('/', (req, res, next) => {
  Todo.find({}).then(todos => {
      res.json(todos);
    })
    .catch(handleError(next));
});

/**
 * GET /todos/:id
 */
router.get('/:id', (req, res, next) => {
  const { id } = req.params;

  Todo.findById(id).then((todo) => {
      if (!todo) return next(notFound());

      res.json(todo);
    })
    .catch(handleError(next));
});

/**
 * POST /todos
 */
router.post('/', (req, res, next) => {
  const todo = new Todo(req.body);

  todo.save().then(() => {
      res.status(201).end();
    })
    .catch(handleError(next));
});

/**
 * PUT /todos/:id
 */
router.put('/:id', (req, res, next) => {
  const { id } = req.params;

  Todo.findById({ _id: id }).then((todo) => {
      if (!todo) return next(notFound());

      const newTodo = Object.assign(todo, req.body);
      return newTodo.save();
    }).then(() => {
      res.status(204).end();
    })
    .catch(handleError(next));
});

/**
 * DELETE /todos/:id
 */
router.delete('/:id', (req, res, next) => {
  const { id } = req.params;

  Todo.findById({ _id: id }).then(todo => {
      if (!todo) return next(notFound());

      return Todo.remove({ _id: id });
    }).then(() => {
      res.status(204).end();
    })
    .catch(handleError(next));
});

module.exports = router;

