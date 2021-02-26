import { shallowMount } from '@vue/test-utils'
import Library from '@/components/Library.vue'

describe('Library.vue', () => {
  it('renders props.msg when passed', () => {
    const msg = 'new message'
    const wrapper = shallowMount(Library, {
      props: { msg }
    })
    expect(wrapper.text()).toMatch(msg)
  })
})
