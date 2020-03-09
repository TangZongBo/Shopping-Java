<template>
  <div class="login_container">
     <div class="login_box">
           <!--logo区域-->
           <div class="avatar_box">
             <img src="../assets/logo.jpg" alt="">
           </div>
           <!--表单区域-->
            <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
                  <!--用户名-->
                 <el-form-item prop="username">
                       <el-input v-model="loginForm.username" prefix-icon="iconfont icon-user"></el-input>
                 </el-form-item>
                  <!--密码-->
                <el-form-item prop="password">
                  <el-input v-model="loginForm.password" prefix-icon="iconfont icon-3702mima" type="password"></el-input>
                </el-form-item>
                   <!--按钮区域-->
                   <el-form-item class="btns">
                     <el-button type="primary" @click="login">登录</el-button>
                     <el-button type="info" @click="resetLoginForm">重置</el-button>
                   </el-form-item>
            </el-form>
     </div>
  </div>
</template>

<script>
export default {
  data () {
 return {
      //表单登陆数据绑定
      loginForm: {
        username: 'admin',
        password: '123456'
      },
      //表单验证规则
      loginFormRules:{
          //用户名合法
          username:[
              {required:true,message:'请输入登录名称',trigger:'blur'},
              {min:3,max:10,message:'长度在3到10个字符',trigger:'blur'}
              ],
          //密码是否合法
          password:[
              {required:true,message:'请输入密码',trigger:'blur'},
              {min:6,max:20,message:'长度在6到20个字符',trigger:'blur'}
          ]
      }
    }
    },
    methods:{
       //重置登陆表单
        resetLoginForm(){
            // console.log(this);
            this.$refs.loginFormRef.resetFields()
        },
        //表单登陆前预验证
        login(){
            const _this = this
             this.$refs.loginFormRef.validate((valid) =>{
                 if(!valid)return;
                  this.$http.post('login',this.loginForm).then(function (resp) {
                          if(resp.data.code!==1){
                              _this.$message.error("登录失败,"+resp.data.message)
                          }else{
                              _this.$message.success("登录成功!")
                              //window.sessionStorage.setItem("token",resp.data.token)
                              window.sessionStorage.setItem("token","123")
                              window.sessionStorage.setItem("username",_this.loginForm.username)
                              _this.$router.push("/home")
                          }
                  })
             });
        }
    }
}
</script>

<style scoped lang="less">
   .login_container{
       background-color: #2b4b6b;
       height: 100%;
   }
   .login_box{
     width:450px;
     height:300px;
     background-color: #fff;
     border-radius: 3px;
     position: absolute;
     left:50%;
     top:50%;
     transform: translate(-50%,-50%);
     .avatar_box{
       height: 130px;
       width: 130px;
       border: 1px solid #eee;
       border-radius: 50%;
       padding: 10px;
       box-shadow: 0 0 10px #ddd;
       position: absolute;
       left: 50%;
       transform: translate(-50%,-50%);
       background-color: #fff;
       img{
         width:100%;
         height:100%;
         border-radius: 50%;
         background-color: #eee;
       }
     }
   }
   .btns{
      display: flex;
      justify-content: flex-end;
   }
   .login_form{
      position: absolute;
      bottom: 0;
     width: 100%;
     padding: 0 20px;
     box-sizing: border-box;
   }
</style>
