require 'test_helper'

class TodosControllerTest < ActionDispatch::IntegrationTest
  test "should get index" do
    get todos_url
    assert_response :success
  end

  test "should get todo" do
    get todo_url(1)
    assert_response :success
  end
end
