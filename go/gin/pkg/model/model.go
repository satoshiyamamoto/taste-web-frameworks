package model

import (
	"gorm.io/gorm"
)

type Todo struct {
	gorm.Model
	Title     string `json:"title"`
	Completed bool   `json:"completed"`
}

func NewTodo(title string, completed bool) *Todo {
	return &Todo{
		Title:     title,
		Completed: completed,
	}

}
