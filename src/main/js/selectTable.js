const selectMovie = () => {
    const title = document.querySelectorAll('#content *[property$=itemreviewed]');
    const directBy = document.querySelectorAll('#content *[rel$=directedBy]');
    const initialReleaseDate = document.querySelectorAll(
        '#content *[property$=initialReleaseDate]'
    );
    const rate = document.querySelectorAll('#content *[property$=average]');
    const votes = document.querySelectorAll('#content *[property$=votes]');
    const recommendations = Array.from(
        document.querySelectorAll('.recommendations-bd dt a[href]')
    ).map(a => a.getAttribute('href').split('/')[4]);

    return {
        data: [title, directBy, initialReleaseDate, rate, votes]
            .map(doms =>
                Array.from(doms)
                    .map(d => d.textContent)
                    .join(',')
            )
            .concat([recommendations.join(', ')]),
        recommendations,
    };
};

module.exports = exports = selectMovie;
