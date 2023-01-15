package main

import (
	"github.com/gin-gonic/gin"
	"github.com/satoshiyamamoto/taste-web-frameworks/go/gin/internal/router"
)

func main() {
	r := gin.Default()
	r.GET("/", router.ListTodos)
	r.GET("/:id", router.GetTodo)
	r.POST("/", router.CreateTodo)
	r.PUT("/:id", router.UpdateTodo)
	r.DELETE("/:id", router.DeleteTodo)
	r.Run()
}
