<template>
    <div>
        <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>订单管理</el-breadcrumb-item>
            <el-breadcrumb-item>订单列表</el-breadcrumb-item>
        </el-breadcrumb>

        <!--卡片视图区域-->
        <el-card class="box-card">
            <div style="text-align: center;margin: 15px;">
                欢迎
                <el-tag type="success">{{userInfo.username}}</el-tag>
                登陆周氏科技后台管理系统
            </div>
        </el-card>

    </div>
</template>

<script>
    export default {
        data(){
            return{
                userInfo:{username:''}
            }
        },
         created() {
             this.getUserInfo()
         },
        methods:{
             getUserInfo(){
                 this.userInfo.username = window.sessionStorage.getItem('username')
             }
        }
    }
</script>

<style lang="less" scoped>

</style>
