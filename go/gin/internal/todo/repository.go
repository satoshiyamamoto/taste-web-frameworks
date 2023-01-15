package todo

import (
	"fmt"

	"github.com/satoshiyamamoto/taste-web-frameworks/go/gin/internal/config"
	"gorm.io/gorm"
)

var db *gorm.DB

func init() {
	db = config.DB
	db.AutoMigrate(&Todo{})
}

func Create(t *Todo) error {
	err := db.Create(t).Error
	if err != nil {
		return err
	}
	return nil
}

func Find(id int) (Todo, error) {
	var t = Todo{}
	err := db.First(&t, id).Error
	if err != nil {
		return Todo{}, err
	}
	return t, nil
}

func FindTodos() []Todo {
	var todos []Todo
	db.Find(&todos)
	return todos
}

func Update(t *Todo) error {
	err := db.Model(t).Select("title", "completed").Updates(t).Error
	if err != nil {
		return err
	}
	return nil
}

func Delete(id int) error {
	err := db.Unscoped().Delete(&Todo{}, id).Error
	if err != nil {
		return err
	}
	return nil
}

func Check() {
	fmt.Println("Hello, GORM!")
}
