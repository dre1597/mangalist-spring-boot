describe('Manga List Application', () => {
  before(() => {
    cy.request('POST', 'http://localhost:8080/mangas', {
      name: 'any_name',
      alternativeName: 'any_alt_name',
      status: 0, // PUBLISHING
      currentChapter: 10,
      finalChapter: 100,
      englishChapter: 5,
      portugueseChapter: 3,
      extraInfo: 'any_extra_info'
    });

    cy.request('POST', 'http://localhost:8080/mangas', {
      name: 'other_name',
      alternativeName: 'other_alt_name',
      status: 1, // HIATUS
      currentChapter: 5,
      finalChapter: 50,
      englishChapter: 2,
      portugueseChapter: 1,
      extraInfo: 'other_extra_info'
    });
  });

  describe('Manga list', () => {
    beforeEach(() => {
      cy.visit('http://localhost:8080');
    });

    it('should display the seeded mangas', () => {
      cy.contains('any_name').should('be.visible');
      cy.contains('other_name').should('be.visible');
    });

    it('should display the homepage with a list of mangas', () => {
      cy.contains('h1', 'Manga List').should('be.visible');

      cy.get('input[name="terms"]').should('be.visible');

      cy.get('table tbody tr').should('have.length.at.least', 1);
    });


    it('should search for mangas by name', () => {
      cy.get('input[name="terms"]').type('any');
      cy.get('button[type="submit"]').click();

      cy.get('table tbody tr').each(($row) => {
        cy.wrap($row).find('td').first().should('contain', 'any');
      });
    });

    it('should navigate to the create manga page', () => {
      cy.contains('a', 'Create').click();

      cy.url().should('include', '/create');
      cy.contains('h1', 'Create Manga').should('be.visible');
    });

    it('should navigate to the edit manga page', () => {
      cy.get('table tbody tr').first().find('a').contains('Edit').click();

      cy.url().should('include', '/edit/');
      cy.contains('h1', 'Create Manga').should('be.visible');
    });

    it('should delete a manga', () => {
      cy.get('table tbody tr').first().find('a').contains('Delete').click();

      cy.url().should('eq', 'http://localhost:8080/');
    });
  });

  describe('Create/Edit Manga Page', () => {
    beforeEach(() => {
      cy.visit('http://localhost:8080/create');
    });

    it('should display the create manga form', () => {
      cy.get('form#mangaForm').should('be.visible');

      cy.get('input[name="name"]').should('be.visible');
      cy.get('input[name="alternativeName"]').should('be.visible');
      cy.get('select[name="status"]').should('be.visible');
      cy.get('input[name="currentChapter"]').should('be.visible');
      cy.get('input[name="finalChapter"]').should('be.visible');
      cy.get('input[name="englishChapter"]').should('be.visible');
      cy.get('input[name="portugueseChapter"]').should('be.visible');
      cy.get('textarea[name="extraInfo"]').should('be.visible');
    });

    it('should submit the create manga form', () => {
      cy.get('input[name="name"]').type('Test Manga');
      cy.get('select[name="status"]').select('0'); // PUBLISHING
      cy.get('input[name="currentChapter"]').type('10');
      cy.get('input[name="finalChapter"]').type('100');
      cy.get('input[name="englishChapter"]').type('5');
      cy.get('input[name="portugueseChapter"]').type('3');
      cy.get('textarea[name="extraInfo"]').type('This is a test manga.');

      cy.get('button[type="submit"]').click();

      cy.url().should('eq', 'http://localhost:8080/');
    });

    it('should cancel and return to the homepage', () => {
      cy.contains('a', 'Cancel').click();

      cy.url().should('eq', 'http://localhost:8080/');
    });
  });

  after(() => {
    cy.request('GET', 'http://localhost:8080/mangas').then((response) => {
      console.log(response);
      response.body.items.forEach((manga) => {
        if (manga.name.startsWith('any') || manga.name.startsWith('other') || manga.name.startsWith('Test Manga')) {
          cy.request('DELETE', `http://localhost:8080/mangas/${manga.id}`);
        }
      });
    });
  });
});

