process.env.NODE_ENV = 'test';

const chai = require('chai');
const chaiHttp = require('chai-http');
const mongoose = require('mongoose');

const app = require('../../app');
const Todo = require('../../models/todo');

const should = chai.should();

chai.use(chaiHttp);

describe('Todos', () => {

  beforeEach((done) => {
    Todo.remove({}).then(() => {
      done();
    });
  });

  describe('GET /todos', () => {
    it('it should GET all the Todos', (done) => {
      chai.request(app)
        .get('/todos')
        .end((err, res) => {
          res.should.have.status(200);
          res.body.should.be.a('array');
          res.body.length.should.be.eq(0);
          done();
        });
    }); 
  });

  describe('GET /todos/:id', () => {
    it('it should GET a todo by the given id', (done) => {
      const todo = new Todo({
        title: 'string',
        completed: false,
      });
      todo.save().then(() => {
        chai.request(app)
          .get(`/todos/${todo._id}`)
          .send(todo)
          .end((err, res) => {
            res.should.have.status(200);
            res.body.should.be.a('object');
            res.body.should.have.property('title');
            res.body.should.have.property('completed');
            done();
          });
      });

    }); 
  });

  describe('POST /todos', () => {
    it('it should not POST a Todo without title field', (done) => {
      const todo = {
        completed: false,
      };
      chai.request(app)
        .post('/todos')
        .send(todo)
        .end((err, res) => {
          res.should.have.status(422);
          done();
        });
    });

    it('it should POST a Todo', (done) => {
      const todo = {
        title: 'string',
        completed: false,
      };
      chai.request(app)
        .post('/todos')
        .send(todo)
        .end((err, res) => {
          res.should.have.status(201);
          done();
        });
    });
  });

  describe('PUT /todos/:id', () => {
    it('it should PUT a todo given the id', (done) => {
      const todo = new Todo({
        title: 'string',
        completed: false,
      });
      todo.save().then(() => {
        chai.request(app)
          .put(`/todos/${todo._id}`)
          .send({ completed: true })
          .end((err, res) => {
            res.should.have.status(204);
            done();
          });
      });
    });
  });

  describe('DELETE /todos/:id', () => {
    it('it should DELETE a todo given the id', (done) => {
      const todo = new Todo({
        title: 'string',
        completed: false,
      });
      todo.save().then(() => {
        chai.request(app)
          .delete(`/todos/${todo._id}`)
          .end((err, res) => {
            res.should.have.status(204);
            done();
          });
      });
    }); 
  });

});
