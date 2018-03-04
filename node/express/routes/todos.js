const mongoose = require('mongoose');
const Todo = require('../models/todo');
const express = require('express');

const router = express.Router();

/**
 * GET /todos
 */
router.get('/', async (req, res, next) => {
  const todos = await Todo.find({});
  res.json(todos);
});

/**
 * GET /todos/:id
 */
router.get('/:id', async (req, res, next) => {
  const { id } = req.params;

  try {
    const todo = await Todo.findById(id);
    if (!todo) throw new Error();
    res.json(todo);
  } catch (e) {
    next(e);
  }
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

