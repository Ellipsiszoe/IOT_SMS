import vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Main',
        component: () => import('../views/Main.vue'),
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('../views/home')
            },
            {
                path: '/user',
                name: 'user',
                component: () => import('../views/User')
            },
            {
                path: '/mall',
                name: 'mall',
                component: () => import('../views/mall')
            },
            {
                path: '/page1',
                name: 'page1',
                component: () => import('../views/other/page1.vue')
            },
            {
                path: '/page2',
                name: 'page2',
                component: () => import('../views/other/page2.vue')
            },
            {
                path: '/record1',
                name: 'record1',
                component: () => import('../views/record/record1.vue')
            },
            {
                path: '/record2',
                name: 'record2',
                component: () => import('../views/record/record2.vue')
            }
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/Login/login.vue')
    }
    
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export default router