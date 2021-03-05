// https://docs.cypress.io/api/introduction/api.html

describe('App Tests', () => {
  beforeEach(() => {
    cy.server()
    cy.route('GET', '/users', {
      username: 'testUser'
    })
    cy.route('get', '/books', {
      data: []
    })
    cy.visit('/')
  })

  afterEach(() => {
    cy.screenshot()
  })

  it('Visits the app root url', () => {
    cy.contains('Library App')
  })

  it('Add new Book', () => {
    cy.contains('Library App')

    cy.get('.new-book').click()

    cy.get('#edit-title').type('testTitle')
    cy.get('#edit-description').type('testDescription')
    cy.get('#edit-readPages').type('0')
    cy.get('#edit-totalPages').type('0')

    cy.route('POST', '/books', {
      data: {
        id: 1,
        title: 'testTitle',
        description: 'testDescription',
        readPages: 0,
        totalPages: 0
      }
    })
    cy.get('#saveEditForm').click()
  })
})

describe('Authorization tests', () => {
  beforeEach(() => {
    cy.server()
    cy.route('GET', '/users', {
      statusCode: 200,
      body: {}
    })
    cy.route('get', '/books', {
      data: []
    })
    cy.visit('/')
  })

  afterEach(() => {
    cy.screenshot()
  })

  it('register', () => {
    cy.get('#edit-username').type('username')
    cy.get('#edit-password').type('password')

    cy.route('POST', '/users/register', {
      username: 'username',
      password: 'password',
      userId: 0
    })

    cy.get('#registerButton').click()
    cy.get('#username').should('visible')
  })

  it('Success login', () => {
    cy.get('#edit-username').type('username')
    cy.get('#edit-password').type('password')

    cy.route('POST', '/users/login', {
      username: 'username',
      password: 'password'
    })

    cy.get('#loginButton').click()
    cy.get('#username').should('visible')
  })

  it('login Failure', () => {
    cy.get('#edit-username').type('########')
    cy.get('#edit-password').type('password')

    cy.route('POST', '/users/login', {
      username: '########',
      password: 'password'
    })

    cy.get('#loginButton').click()
    cy.get('#error').should('visible')
  })
})
