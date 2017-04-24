require 'test_helper'

class TodosControllerTest < ActionDispatch::IntegrationTest
  test "should get todos" do
    get todos_url
    assert_response :success
  end

  test "should get todo" do
    todo = todos(:one)
    get todo_url(todo)
    assert_response :success
  end

  test "should raise error when todo does not exists" do
    assert_raises ActiveRecord::RecordNotFound do
      get todo_url(1)
    end
  end

  test "should create todo" do
    assert_difference('Todo.count') do
      post todos_url, params: { 
        title: 'title', completed: false }
    end
    assert_response :created
  end

  test "should raise error when title to be blank" do
    assert_raises ActiveRecord::RecordInvalid do
      post todos_url, params: { 
        title: ' ', completed: false }
    end
  end

  test "should destroy todo" do
    todo = todos(:one)

    assert_difference('Todo.count', -1) do
      delete todo_url(todo)
    end
    assert_response :no_content
  end

  test "should update todo" do
    todo = todos(:one)

    put todo_url(todo), params: { 
      title: 'updated', completed: false }
    assert_response :no_content

    todo.reload
    assert_equal 'updated', todo.title
  end
end
