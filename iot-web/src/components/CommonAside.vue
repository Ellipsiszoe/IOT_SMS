<template>
  <el-menu
    default-active="1-4-1"
    class="el-menu-vertical-demo"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    @open="handleOpen"
    @close="handleClose"
    :collapse="isCollapse"
  >
    <h3>{{isCollapse ? '物联网' : '物联网监控系统'}}</h3>
    <el-menu-item
      @click="clickMenu(item)"
      v-for="item in noChildren" 
      :index="item.path" 
      :key="item.path"
    >
    <!-- 主要要用i :class="'el-icon' + item.icon" 注意检查空格-->
      <i :class="'el-icon-' + item.icon"></i>
      <span slot="title">{{ item.label }}</span>
    </el-menu-item>
    <!-- 一级菜单 -->
    <el-submenu 
      v-for="item in hasChildren" 
      :index="item.path"
      :key="item.path"
      >
      <template slot="title">
        <i :class="'el-icon-' + item.icon"></i>
        <span slot="title">{{item.label}}</span>
      </template>
      <!-- 二级菜单 -->
      <el-menu-item-group
        v-for="(subItem, subIndex) in item.children"
        :key="subItem.path"
        >
        <el-menu-item @click="clickMenu(subItem)" :index="subIndex" >{{subItem.label}}</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<style lang="less" scoped>
/* 使用less样式，同时不影响其他样式 */
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-menu {
  height: 100%;
  border: none;
  // 覆盖h3样式
  h3 {
    color: #fff;
    text-align: center;
    line-height: 48px;
  }
}
</style>

<script>
export default {
  data() {
    return {
      // isCollapse: false,
      menu: [
        {
          path: "/",
          name: "home",
          label: "首页",
          icon: "s-home",
          url: "Home/Home",
        },
        {
          path: "/mall",
          name: "mall",
          label: "设备管理",
          icon: "video-play",
          url: "MallManage/MallManage",
        },
        {
          path: "/user",
          name: "user",
          label: "用户管理",
          icon: "user",
          url: "UserManage/UserManage",
        },
        {
          path: "/other",
          label: "数据统计",
          icon: "location",
          children: [
            {
              path: "/page1",
              name: "page1",
              label: "按天统计",
              icon: "setting",
              url: "Other/PageOne",
            },
            {
              path: "/page2",
              name: "page2",
              label: "按周统计",
              icon: "setting",
              url: "Other/PageTwo",
            },
          ],
        },
        {
          path: "/record",
          label: "数据记录",
          icon: "location",
          children: [
            {
              path: "/record1",
              name: "record1",
              label: "DHT11数据",
              icon: "setting",
              url: "Record/record1",
            },
            {
              path: "/record2",
              name: "record2",
              label: "随机数据",
              icon: "setting",
              url: "Record/record2",
            },
          ],
        },
      ],
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    // 点击事件，路由跳转
    clickMenu(item) {
      this.$router.push({
          name: item.name
      })
      this.$store.commit('selectMenu', item)

    }
  },
  computed: {
    noChildren() {
      return this.menu.filter((item) => !item.children);
    },
    hasChildren() {
      return this.menu.filter((item) => item.children);
    },
    isCollapse() {
      return this.$store.state.tab.isCollapse
    }
  },
};
</script>
