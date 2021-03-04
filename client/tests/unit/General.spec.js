import { shallowMount } from '@vue/test-utils'
import Library from '@/views/Library.vue'
import ViewBook from '@/views/ViewBook.vue'
import App from '@/App'

describe('Logo click Tests', () => {
  it('check logo click route from inner path', () => {
    const mockedRouter = {
      push: jest.fn()
    }
    const mockedRoute = {
      fullPath: '/book/1'
    }
    const wrapper = shallowMount(App, {
      global: {
        mocks: {
          $route: mockedRoute,
          $router: mockedRouter
        }
      }
    })
    wrapper.find('.title').trigger('click')
    expect(mockedRouter.push).toHaveBeenCalledTimes(1)
    expect(mockedRouter.push).toHaveBeenCalledWith('/')
  })

  it('check logo click route from main page', () => {
    const mockedRouter = {
      go: jest.fn()
    }
    const mockedRoute = {
      fullPath: '/'
    }
    const wrapper = shallowMount(App, {
      global: {
        mocks: {
          $route: mockedRoute,
          $router: mockedRouter
        }
      }
    })
    wrapper.find('.title').trigger('click')
    expect(mockedRouter.go).toHaveBeenCalledTimes(1)
    expect(mockedRouter.go).toHaveBeenCalledWith(0)
  })
})

describe('Loading tests', () => {
  it('check loading for Library component', () => {
    const wrapper = shallowMount(Library)
    expect(wrapper.vm.loading).toBe(true)
    expect(wrapper.find('.loading').exists()).toBe(true)
    expect(wrapper.find('#LibrarySection').exists()).toBe(false)
  })
  it('check loading for ViewBook component', () => {
    const wrapper = shallowMount(ViewBook, {
      props: { id: 1 }
    })
    expect(wrapper.vm.loading).toBe(true)
    expect(wrapper.find('.loading').exists()).toBe(true)
    expect(wrapper.find('#bookViewSection').exists()).toBe(false)
  })
})
