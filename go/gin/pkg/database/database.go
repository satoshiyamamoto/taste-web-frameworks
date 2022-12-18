package database

import (
	"fmt"
	"log"

	"github.com/satoshiyamamoto/taste-web-frameworks/go/gin/pkg/model"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

var db *gorm.DB

func init() {
	var err error
	db, err = gorm.Open(sqlite.Open("todo.db"), &gorm.Config{})
	if err != nil {
		log.Fatal("failed to connect database")
	}

	db.Logger = db.Logger.LogMode(logger.Info)
	db.AutoMigrate(&model.Todo{})
}

func CreateTodo(t *model.Todo) error {
	err := db.Create(t).Error
	if err != nil {
		return err
	}
	return nil
}

func FindTodo(id int) (model.Todo, error) {
	var t = model.Todo{}
	err := db.First(&t, id).Error
	if err != nil {
		return model.Todo{}, err
	}
	return t, nil
}

func FindTodos() []model.Todo {
	var todos []model.Todo
	db.Find(&todos)
	return todos
}

func UpdateTodo(t *model.Todo) error {
	err := db.Model(t).Select("title", "completed").Updates(t).Error
	if err != nil {
		return err
	}
	return nil
}

func DeleteTodo(id int) error {
	err := db.Unscoped().Delete(&model.Todo{}, id).Error
	if err != nil {
		return err
	}
	return nil
}

func Check() {
	fmt.Println("Hello, GORM!")
}
