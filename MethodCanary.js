/**
    classInfo
        {int access
         String name
         String superName
         String[] interfaces}

     methodInfo
         {int access
         String name
         String desc}
**/
/**
1.不能修改文件中这两个方法的名称和参数:function isExclude(classInfo,methodInfo) and function isInclude(classInfo,methodInfo)
2.这两个方法的返回值必须是bool类型，isExclude方法默认返回false，isInclude默认返回true
3.参数classInfo有如下字段: int access,String name,String superName,String[] interfaces
4.参数methodInfo有如下字段: int access,String name,String desc
5.使用javascript语言写MethodCanary.js文件
*/
function isExclude(classInfo,methodInfo){
    return false
}

function isInclude(classInfo,methodInfo){
    return true
}