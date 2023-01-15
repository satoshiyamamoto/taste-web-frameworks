package router

import (
	"errors"
	"log"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
	"github.com/satoshiyamamoto/taste-web-frameworks/go/gin/internal/todo"
	"gorm.io/gorm"
)

func GetTodo(c *gin.Context) {
	p := c.Param("id")
	id, err := strconv.Atoi(p)
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	t, err := todo.Find(id)
	if errors.Is(err, gorm.ErrRecordNotFound) {
		c.Status(http.StatusNotFound)
		return
	}
	if err != nil {
		log.Println("cloudn't get todo", err)
		c.Status(http.StatusInternalServerError)
	}

	c.JSON(http.StatusOK, t)
}

func ListTodos(c *gin.Context) {
	todos := todo.FindTodos()

	c.JSON(http.StatusOK, todos)
}

func CreateTodo(c *gin.Context) {
	t := &todo.Todo{}
	err := c.ShouldBindJSON(t)
	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	err = todo.Create(t)
	if err != nil {
		c.Status(http.StatusInternalServerError)
		return
	}

	c.JSON(http.StatusCreated, t)
}

func UpdateTodo(c *gin.Context) {
	p := c.Param("id")
	id, err := strconv.Atoi(p)
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	_, err = todo.Find(id)
	if errors.Is(err, gorm.ErrRecordNotFound) {
		c.Status(http.StatusNotFound)
		return
	}
	if err != nil {
		log.Println("cloudn't get todo", err)
		c.Status(http.StatusInternalServerError)
	}

	t := todo.New("", false)
	err = c.ShouldBindJSON(t)
	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}
	t.ID = uint(id)
	todo.Update(t)

	c.Status(http.StatusNoContent)
}

func DeleteTodo(c *gin.Context) {
	p := c.Param("id")
	id, err := strconv.Atoi(p)
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	todo.Delete(id)

	c.Status(http.StatusNoContent)
}
