<template>
    <div>
        <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>权限管理</el-breadcrumb-item>
            <el-breadcrumb-item>角色列表</el-breadcrumb-item>
        </el-breadcrumb>
        <!--卡片视图-->
        <el-card>
            <!--添加角色按钮-->
            <el-row>
                <el-col>
                    <el-button type="primary">添加角色</el-button>
                </el-col>
            </el-row>

            <!--角色列表区域-->
            <el-table :data="roleList" border stripe>
                <!--展开列-->
                <el-table-column type="expand">
                     <template slot-scope="scope">
                         <!--栅栏-->
                            <el-row :class="['bpbottom',i1===0?'bptop':'','vcenter']" v-for="(item1,i1) in scope.row.children" :key="item1.id">
                                <!--渲染一级权限-->
                                <el-col :span="5">
                                    <el-tag closable @close="removeRightById(scope.row,item1.id)">{{item1.authName}}</el-tag>
                                    <i class="el-icon-caret-right"></i>
                                </el-col>
                                <!--渲染二级和三级权限-->
                                <el-col :span="19">
                                        <!--通过for循环渲染-->
                                        <el-row :class="[i2===0?'':'bptop','vcenter']" v-for="(item2,i2) in item1.children" :key="item2.id">
                                            <el-col :span="6">
                                                <el-tag type="success" closable @close="removeRightById(scope.row,item2.id)">{{item2.authName}}</el-tag>
                                                <i class="el-icon-caret-right"></i>
                                            </el-col>
                                            <el-col :span="18">
                                                <el-tag v-for="(item3,i3) in item2.children" :key="item3.id" type="warning" closable @close="removeRightById(scope.row,item3.id)">{{item3.authName}}</el-tag>
                                            </el-col>
                                        </el-row>
                                </el-col>
                            </el-row>
                            <!--<pre>
                                {{scope.row}}
                            </pre>-->
                     </template>
                </el-table-column>
                <!--索引列-->
                <el-table-column type="index" label="#"></el-table-column>
                <el-table-column label="角色名称" prop="roleName"></el-table-column>
                <el-table-column label="角色描述" prop="roleDesc"></el-table-column>
                <el-table-column label="操作" width="300px">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" icon="el-icon-search">编辑</el-button>
                        <el-button size="mini" type="danger" icon="el-icon-delete">删除</el-button>
                        <el-button size="mini" type="warning" icon="el-icon-setting" @click="showSetRightDialog(scope.row)">分配权限</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!--分配权限对话框-->
        <el-dialog
                title="分配权限"
                :visible.sync="setRightDialogVisible"
                width="50%" @close="setRightDialogClosed">
            <el-tree :data="rightList" :props="treeProps" show-checkbox node-key="id" default-expand-all
                     :default-checked-keys="defKeys" ref="treeRef">

            </el-tree>
            <span slot="footer" class="dialog-footer">
        <el-button @click="setRightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="allotRights">确 定</el-button>
            </span>
        </el-dialog>


    </div>
</template>

<script>
    export default {
         data(){
             return{
                 //所有角色列表数据
                 roleList:[],
                 //权限数据
                 rightList:[],
                 //分配对话框显示与隐藏
                 setRightDialogVisible:false,
                 //树形控件的属性绑定对象
                 treeProps:{
                     label:'authName',//看到那个值
                     children:'children'//父子节点用哪个实现嵌套的
                 },
                 //默认选中节点id值数组
                 //点击分配权限去获取当前角色的三级权限的id值 只需要三级权限
                 //defKeys:[105,106]
                 defKeys:[],
                 //当前即将被分配角色的id
                 roleId:''
             }
         },
        created() {
           this.getRolesList()
        },
        methods:{
             getRolesList(){
                 const _this = this
                 /*this.$http.get('roles').then(function (resp) {
                       if(resp.meta.status!==200){
                           _this.$message.error('获取角色列表错误!')
                       }
                       this.roleList = resp.data
                 })*/
                 this.roleList = [
                     {
                         id:1,
                         roleName:'主管',
                         roleDesc:'技术负责人',
                         children:[
                             {
                                 id:101,
                                 authName:'商品管理',
                                 path:'goods',
                                 children:[
                                     {
                                         id:104,
                                         authName:'商品列表',
                                         path:'goods',
                                         children:[
                                             {
                                                 id:116,
                                                 authName:'商品修改',
                                                 path:'goods'
                                             },
                                             {
                                                 id:117,
                                                 authName:'更新商品图片',
                                                 path:'goods',
                                             },
                                             {
                                                 id:118,
                                                 authName:'更新商品属性',
                                                 path:'goods'
                                             },
                                             {
                                                 id:119,
                                                 authName:'更新商品状态',
                                                 path:'goods'
                                             },
                                             {
                                                 id:120,
                                                 authName:'获取商品详情',
                                                 path:'goods'
                                             }
                                         ]
                                     },
                                     {
                                         id:121,
                                         authName:'商品分类',
                                         path:'goods',
                                         children:[
                                             {
                                                 id:122,
                                                 authName:'添加分类',
                                                 path:'goods'
                                             },
                                             {
                                                 id:123,
                                                 authName:'删除分类',
                                                 path:'goods'
                                             },
                                             {
                                                 id:124,
                                                 authName:'获取分类详情',
                                                 path:'goods'
                                             }
                                         ]
                                     }/*,
                                     {
                                         id:125,
                                         authName:'分类参数',
                                         path:'goods',
                                         children:[
                                             {
                                                 id:126,
                                                 authName:'获取参数列表',
                                                 path:'goods'
                                             },
                                             {
                                                 id:127,
                                                 authName:'创建商品参数',
                                                 path:'goods'
                                             },
                                             {
                                                 id:128,
                                                 authName:'删除商品参数',
                                                  path:'goods'
                                             }
                                         ]
                                     }*/
                                 ]
                             },
                             {
                                 id:129,
                                 authName:'订单管理',
                                 path:'goods',
                                 children:[
                                     {
                                         id:130,
                                         authName:'订单列表',
                                         path:'goods',
                                         children:[
                                             {
                                                 id:131,
                                                 authName:'添加订单',
                                                 path:'goods'
                                             },
                                             {
                                                 id:132,
                                                 authName:'订单更新',
                                                 path:'goods'
                                             },
                                             {
                                                 id:133,
                                                 authName:'获取订单详情',
                                                 path:'goods'
                                             }
                                         ]
                                     }
                                 ]
                             }
                         ]
                     },
                     {
                         id:2,
                         roleName:'测试角色',
                         roleDesc:'测试角色描述'
                     },
                     {
                         id:3,
                         roleName:'测试角色2',
                         roleDesc:'测试描述12'
                     },
                     {
                         id:4,
                         roleName:'大发送到',
                         roleDesc:'阿斯蒂芬'
                     },
                     {
                         id:5,
                         roleName:'dsdf',
                         roleDesc:'sf'
                     }
                 ]
             },
            //根据id删除对应的权限
            async removeRightById(role,rightId){
                //弹框提示用户是否要删除
                /*const confirmResult = await
                    this.$confirm('此操作将永久删除该权限, 是否继续?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }
                    ).catch(err => err)
                if(confirmResult!=='confirm'){
                    return this.$message.info('用户取消了删除!')
                }

                const {data:res} = await this.$http.delete('roles/${role.id}/rights/${rightId}')
                if(res.meta.status!==200){
                    this.$message.error('用户删除权限失败!')
                }
                // 不建议直接重新加载权限 会发生反转
                //this.getRolesList()
                //直接把返回渲染上去就行了 体验非常好
                role.children = res.data*/
            },
            //分配权限对话框
            async showSetRightDialog(role){
                 this.roleId = role.id
                 //获取所有权限
                /*const {data:res} = await
                    this.$http.get('rights/tree')
                if(res.meta.status!==200){
                    this.$message.error('获取权限数据失败!')
                }
                this.rightList = res.data*/
                this.rightList = [
                    {
                        id:101,
                        authName:'商品管理',
                        path:null,
                        pid:0,
                        children:[
                            {
                                id:104,
                                authName:'商品列表',
                                path:null,
                                pid:101,
                                children:[
                                    {
                                        id:116,
                                        authName:'商品修改',
                                        path:null,
                                        pid:'104,101'
                                    },
                                    {
                                        id:117,
                                        authName:'更新商品图片',
                                        path:null,
                                        pid:'104,101'
                                    },
                                    {
                                        id:118,
                                        authName:'更新商品属性',
                                        path:null,
                                        pid:'104,101'
                                    },
                                    {
                                        id:119,
                                        authName:'更新商品状态',
                                        path:null,
                                        pid:'104,101'
                                    },
                                    {
                                        id:120,
                                        authName:'获取商品详情',
                                        path:null,
                                        pid:'104,101'
                                    }
                                ]
                            },
                            {
                                id:121,
                                authName:'商品分类',
                                path:null,
                                pid:101,
                                children:[
                                    {
                                        id:122,
                                        authName:'添加分类',
                                        path:null,
                                        pid:'121,101'
                                    },
                                    {
                                        id:123,
                                        authName:'删除分类',
                                        path:null,
                                        pid:'121,101'
                                    },
                                    {
                                        id:124,
                                        authName:'获取分类详情',
                                        path:null,
                                        pid:'121,101'
                                    }
                                ]
                            },
                            {
                                id:125,
                                authName:'分类参数',
                                path:null,
                                pid:101,
                                children:[
                                    {
                                        id:126,
                                        authName:'获取参数列表',
                                        path:null,
                                        pid:'125,101'
                                    },
                                    {
                                        id:127,
                                        authName:'创建商品参数',
                                        path:null,
                                        pid:'125,101'
                                    },
                                    {
                                        id:128,
                                        authName:'删除商品参数',
                                        path:null,
                                        pid:'125,101'
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        id:129,
                        authName:'订单管理',
                        path:null,
                        pid:0,
                        children:[
                            {
                                id:130,
                                authName:'订单列表',
                                path:null,
                                pid:129,
                                children:[
                                    {
                                        id:131,
                                        authName:'添加订单',
                                        path:null,
                                        pid:'129,130'
                                    },
                                    {
                                        id:132,
                                        authName:'订单更新',
                                        path:null,
                                        pid:'129,130'
                                    },
                                    {
                                        id:133,
                                        authName:'获取订单详情',
                                        path:null,
                                        pid:'129,130'
                                    }
                                ]
                            }
                        ]
                    }
                ]

                //获取三级节点id
                this.getLeafKeys(role,this.defKeys)

                this.setRightDialogVisible = true
            },

            //递归获取角色下所有三级权限的id 保存到defKeys中
            getLeafKeys(node,arr){
                 //当前node节点不包含children 则是三级节点
                 if(!node.children){
                    // console.log(node.id)
                     return  arr.push(node.id)
                 }

                 node.children.forEach(item =>
                     this.getLeafKeys(item,arr))
            },
             //监听分配权限对话框关闭事件
            setRightDialogClosed(){
                 this.defKeys = []
                this.setRightDialogVisible = false
            },
            //点击为角色分配权限
            async allotRights(){
                 const keys = [
                     ...this.$refs.treeRef.
                     getCheckedKeys(),
                     ...this.$refs.treeRef.
                     getHalfCheckedKeys()
                 ]

                //console.log(keys)
                const idStr = keys.join(',')

                /*const {data:res} = await this.$http.post('/roles/${this.roleId}/rights',{rids:idStr})
                if(res.meta.status!==200){
                    this.$message.error('分配权限失败!')
                }

                this.$message.success('分配权限成功!')
                //重新加载角色列表
                this.getRolesList()*/
                this.setRightDialogVisible = false
            }
        }
    }
</script>

<style lang="less" scoped>
    .el-tag{
        margin: 7px;
    }

    .bptop{
        border-top: 1px solid #eee;
    }

    .bpbottom{
        border-bottom: 1px solid #eee;
    }
    .vcenter{
        display: flex;
        align-items: center;
    }
</style>
