import { shallowMount } from '@vue/test-utils'
import { errorMessages } from '@/assets/resources'
import RegistrationForm from '@/components/RegistrationForm'

describe('RegisterForm component tests', () => {
  it('check clicking buttons', () => {
    const username = 'username'
    const password = '123456'
    const wrapper = shallowMount(RegistrationForm, {
      global: {
        mocks: {
          $logger: {
            debug: jest.fn(),
            info: jest.fn()
          }
        }
      }
    })
    wrapper.find('#edit-username').setValue(username)
    wrapper.find('#edit-password').setValue(password)
    wrapper.find('#loginButton').trigger('click')

    expect(wrapper.emitted()).toHaveProperty('login')
    const loginEvent = wrapper.emitted('login')
    expect(loginEvent).toHaveLength(1)
    expect(loginEvent[0]).toEqual([username, password])

    wrapper.find('#registerButton').trigger('click')

    expect(wrapper.emitted()).toHaveProperty('register')
    const registerEvent = wrapper.emitted('register')
    expect(registerEvent).toHaveLength(1)
    expect(registerEvent[0]).toEqual([username, password])
  })

  it('check form bounds', () => {
    const correctUsername = 'username'
    const correctPassword = 'password'
    const longUsername = 'a'.repeat(50)
    const invalidUsername = '#'
    const shortPassword = '123'
    const longPassword = 'a'.repeat(50)
    const wrapper = shallowMount(RegistrationForm, {
      global: {
        mocks: {
          $logger: {
            debug: jest.fn(),
            info: jest.fn()
          }
        }
      }
    })

    wrapper.find('#edit-username').setValue(longUsername)
    wrapper.find('#edit-password').setValue(correctPassword)
    wrapper.find('#loginButton').trigger('click')
    wrapper.find('#registerButton').trigger('click')

    wrapper.find('#edit-username').setValue(invalidUsername)
    wrapper.find('#loginButton').trigger('click')
    wrapper.find('#registerButton').trigger('click')

    wrapper.find('#edit-username').setValue(correctUsername)
    wrapper.find('#edit-password').setValue(longPassword)
    wrapper.find('#loginButton').trigger('click')
    wrapper.find('#registerButton').trigger('click')

    wrapper.find('#edit-password').setValue(shortPassword)
    wrapper.find('#loginButton').trigger('click')
    wrapper.find('#registerButton').trigger('click')

    expect(wrapper.emitted()).toHaveProperty('errorOccurred')
    const errorEvent = wrapper.emitted('errorOccurred')
    expect(errorEvent).toHaveLength(8)
    expect(errorEvent[0]).toEqual([errorMessages.tooLongUsername])
    expect(errorEvent[1]).toEqual([errorMessages.tooLongUsername])
    expect(errorEvent[2]).toEqual([errorMessages.invalidUsername])
    expect(errorEvent[3]).toEqual([errorMessages.invalidUsername])
    expect(errorEvent[4]).toEqual([errorMessages.tooLongPassword])
    expect(errorEvent[5]).toEqual([errorMessages.tooLongPassword])
    expect(errorEvent[6]).toEqual([errorMessages.tooShortPassword])
    expect(errorEvent[7]).toEqual([errorMessages.tooShortPassword])
  })
})
