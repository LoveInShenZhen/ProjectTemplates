package models.todolist

/*
    定义ToDo任务的优先级
 */
enum class ToDoPriority(val code: Int, val desc: String) {
    Low(1, "低优先级"),
    Normal(2, "普通优先级"),
    High(3, "高优先级");

    companion object {

        /**
         * 检查指定的 code 是否是一个有效的 ToDo任务的优先级
         */
        fun verify(code: Int): Boolean {
            return values().map { it.code }.toSet().contains(code)
        }
    }
}
