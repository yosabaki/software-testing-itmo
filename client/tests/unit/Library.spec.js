import { flushPromises, shallowMount } from '@vue/test-utils'
import Library from '@/views/Library.vue'
import api from '../__mocks__/fake-api'

describe('Library view tests', () => {
  beforeEach(() => {
    api.reloadBooks()
  })

  it('check data is loaded', async () => {
    const wrapper = await shallowMount(Library, {
      global: {
        mocks: {
          $logger: {
            debug: jest.fn(),
            info: jest.fn()
          }
        }
      }
    })
    expect(wrapper.vm.books.length).toBeGreaterThan(0)
  })

  it('check data is visible', async () => {
    const wrapper = shallowMount(Library, {
      global: {
        mocks: {
          $logger: {
            debug: jest.fn(),
            info: jest.fn()
          }
        }
      }
    })
    await flushPromises()
    expect(wrapper.vm.loading).toBe(false)
    const length = wrapper.vm.books.length
    expect(length).toBeGreaterThan(0)
    expect(wrapper.find('.loading').exists()).toBe(false)
    expect(wrapper.find('#LibrarySection').exists()).toBe(true)
    expect(wrapper.findAll('#LibrarySection .book-list li').length).toEqual(length)
  })

  it('check remove', async () => {
    const wrapper = shallowMount(Library, {
      global: {
        mocks: {
          $logger: {
            debug: jest.fn(),
            info: jest.fn()
          }
        }
      }
    })
    await flushPromises()
    expect(wrapper.vm.loading).toBe(false)
    const length = wrapper.vm.books.length
    expect(length).toBeGreaterThan(0)
    await wrapper.find('#LibrarySection .book-list .destroy').trigger('click')
    await flushPromises()
    expect(wrapper.vm.books.length).toBe(length - 1)
    expect(wrapper.findAll('#LibrarySection .book-list li').length).toEqual(length - 1)
  })

  it('check element view', async () => {
    const mockedRouter = {
      push: jest.fn()
    }
    const wrapper = shallowMount(Library, {
      global: {
        mocks: {
          $logger: {
            debug: jest.fn(),
            info: jest.fn()
          },
          $router: mockedRouter
        }
      }
    })
    await flushPromises()
    await wrapper.find('#LibrarySection .book-list li .book').trigger('click')
    expect(mockedRouter.push).toBeCalledTimes(1)
    expect(mockedRouter.push).toHaveBeenCalledWith('/book/1')
  })
})
