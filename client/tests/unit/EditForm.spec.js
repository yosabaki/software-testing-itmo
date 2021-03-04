import { shallowMount } from '@vue/test-utils'
import EditForm from '@/components/EditForm.vue'
import { errorMessages } from '@/assets/resources'

describe('EditForm component tests', () => {
  it('check clicking buttons', () => {
    const book = {
      title: 'smth',
      description: '',
      readPages: 0,
      totalPages: 10
    }
    const wrapper = shallowMount(EditForm, {
      props: {
        title: book.title,
        description: book.description,
        readPages: book.readPages,
        totalPages: book.totalPages
      }
    })
    wrapper.find('#saveEditForm').trigger('click')

    expect(wrapper.emitted()).toHaveProperty('editDone')
    const editDoneEvent = wrapper.emitted('editDone')
    expect(editDoneEvent).toHaveLength(1)
    expect(editDoneEvent[0]).toEqual([book])

    wrapper.find('#cancelEditForm').trigger('click')

    expect(wrapper.emitted()).toHaveProperty('editCancel')
    const editCancelEvent = wrapper.emitted('editCancel')
    console.log(editCancelEvent)
    expect(editCancelEvent).toHaveLength(1)
  })

  it('check form bounds', () => {
    const book = {
      title: '#',
      description: '#',
      readPages: 0,
      totalPages: 0
    }
    const wrapper = shallowMount(EditForm, {
      props: {
        title: book.title,
        description: book.description,
        readPages: book.readPages,
        totalPages: book.totalPages
      }
    })
    // readPages < 0
    wrapper.find('#edit-readPages').setValue(-1)
    wrapper.find('#saveEditForm').trigger('click')
    // readPages > totalPages
    wrapper.find('#edit-readPages').setValue(1)
    wrapper.find('#saveEditForm').trigger('click')
    // emptyTitle
    wrapper.find('#edit-readPages').setValue(0)
    wrapper.find('#edit-title').setValue('')
    wrapper.find('#saveEditForm').trigger('click')
    // too long title
    wrapper.find('#edit-title').setValue('#'.repeat(256))
    wrapper.find('#saveEditForm').trigger('click')
    // too long description
    wrapper.find('#edit-title').setValue('#')
    wrapper.find('#edit-description').setValue('#'.repeat(1001))
    wrapper.find('#saveEditForm').trigger('click')
    // all passed
    wrapper.find('#edit-description').setValue('#')
    wrapper.find('#saveEditForm').trigger('click')

    expect(wrapper.emitted()).toHaveProperty('errorOccurred')
    const errorEvent = wrapper.emitted('errorOccurred')
    expect(errorEvent).toHaveLength(5)
    expect(errorEvent[0]).toEqual([errorMessages.lessThanZero])
    expect(errorEvent[1]).toEqual([errorMessages.readGreaterThanTotal])
    expect(errorEvent[2]).toEqual([errorMessages.requiredTitle])
    expect(errorEvent[3]).toEqual([errorMessages.tooLongTitle])
    expect(errorEvent[4]).toEqual([errorMessages.tooLongDescription])

    expect(wrapper.emitted()).toHaveProperty('editDone')
    const editDoneEvent = wrapper.emitted('editDone')
    expect(editDoneEvent).toHaveLength(1)
  })
})
