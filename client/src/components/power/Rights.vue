<template>
    <div>
        <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>权限管理</el-breadcrumb-item>
            <el-breadcrumb-item>权限列表</el-breadcrumb-item>
        </el-breadcrumb>

        <!--卡片视图区域-->
        <el-card>
            <el-table :data="rightsList" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="权限名称" prop="authName"></el-table-column>
                <el-table-column label="路径" prop="path"></el-table-column>
                <el-table-column label="权限等级" prop="level">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.level === '0'">一级</el-tag>
                        <el-tag type="success" v-else-if="scope.row.level === '1'">二级</el-tag>
                        <el-tag type="warning" v-else>三级</el-tag>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
    </div>
</template>

<script>
    export default {
       data(){
           return{
               //所有的权限列表
               rightsList:[]
           }
       },
        created() {
           //获取所有的权限
           this.getRightsList()
        },
        methods:{
           //获取权限列表
           getRightsList(){
               const _this = this
               this.rightsList = [
                   {
                       id:1,
                       authName:'商品管理',
                       level:'0',
                       path:'goods',
                       pid:0
                   },
                   {
                       id:2,
                       authName:'订单管理',
                       level:'0',
                       path:'orders',
                       pid:0
                   },
                   {
                       id:3,
                       authName:'权限管理',
                       level:'0',
                       path:'rights',
                       pid:0
                   },
                   {
                       id:4,
                       authName:'商品列表',
                       level:'1',
                       path:'goods',
                       pid:0
                   },
                   {
                       id:5,
                       authName:'添加商品',
                       level:'2',
                       path:'goods',
                       pid:0
                   },
                   {
                       id:6,
                       authName:'订单列表',
                       level:'1',
                       path:'orders',
                       pid:0
                   },
                   {
                       id:7,
                       authName:'添加订单',
                       level:'2',
                       path:'orders',
                       pid:0
                   },
                   {
                       id:8,
                       authName:'用户列表',
                       level:'1',
                       path:'users',
                       pid:0
                   }
               ]
               /*this.$http.get('rights/list').then(function (resp) {
                   if(resp.meta.status!==200){
                       _this.$message.error('获取权限列表失败!')
                   }
                     this.rightsList = resp.data
               })*/
           }
        }
    }
</script>

<style lang="less" scoped>

</style>
