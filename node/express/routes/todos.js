const mongoose = require('mongoose');
const Todo = require('../models/todo');
const express = require('express');

const router = express.Router();

/**
 * GET /todos
 */
router.get('/', (req, res, next) => {
  Todo.find({}).then(todos => {
      res.json(todos);
    })
    .catch(next);
});

/**
 * GET /todos/:id
 */
router.get('/:id', (req, res, next) => {
  const { id } = req.params;

  Todo.findById(id).then((todo) => {
      if (!todo) return Promise.reject();

      res.json(todo);
    })
    .catch(next);
});

/**
 * POST /todos
 */
router.post('/', (req, res, next) => {
  const todo = new Todo(req.body);

  todo.save().then(() => {
      res.status(201).end();
    })
    .catch(next);
});

/**
 * PUT /todos/:id
 */
router.put('/:id', (req, res, next) => {
  const { id } = req.params;

  Todo.findById({ _id: id }).then((todo) => {
      if (!todo) return Promise.reject();

      const newTodo = Object.assign(todo, req.body);
      return newTodo.save();
    }).then(() => {
      res.status(204).end();
    })
    .catch(next);
});

/**
 * DELETE /todos/:id
 */
router.delete('/:id', (req, res, next) => {
  const { id } = req.params;

  Todo.findById({ _id: id }).then(todo => {
      if (!todo) return Promise.reject();

      return Todo.remove({ _id: id });
    }).then(() => {
      res.status(204).end();
    })
    .catch(next);
});

module.exports = router;

