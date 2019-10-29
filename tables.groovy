import static Constants.*

studentColumns = ['sid', 'sname', 'gradYear', 'majorId' ]
gradeColumns = [ 'cid', 'studentId', 'grade' ]
majors = [ 10: 'English', 20: 'Physics', 30: 'Computer Science' ]

mapMajorIds = { row -> majors[row.majorId] }
upperCaseName = { row -> row.sname.toUpperCase() }
yearsSinceGraduation = { row -> (new Date().year + 1900) - row.gradYear }

listStudents =
    [[ 1, 'joe', 2004, 10 ],
     [ 2, 'amy', 2004, 20 ],
     [ 3, 'max', 2005, 10 ],
     [ 4, 'sue', 2005, 20 ],
     [ 5, 'bob', 2003, 30 ],
     [ 6, 'kim', 2001, 20 ],
     [ 7, 'art', 2004, 30 ],
     [ 8, 'pat', 2001, 20 ],
     [ 9, 'lee', 2004, 10 ] ]

dupListStudents =
    [[ 1, 'joe', 2009, 10 ],
     [ 2, 'amy', 2004, 20 ],
     [ 3, 'max', 2005, 10 ],
     [ 4, 'sue', 2005, 20 ],
     [ 5, 'amy', 2003, 30 ],
     [ 6, 'kim', 2001, 20 ],
     [ 7, 'joe', 2004, 30 ],
     [ 8, 'pat', 2001, 20 ],
     [ 9, 'bob', 2004, 10 ] ]

listGrades =
    [[ 10, 1, 95 ],
     [ 10, 2, 87 ],
     [ 10, 3, 60 ],
     [ 10, 4, 90 ],
     [ 10, 5, 77 ],
     [ 10, 6, 60 ],
     [ 10, 7, 85 ],
     [ 10, 8, 97 ],
     [ 10, 9, 80 ] ]

students = new RowTable(studentColumns, listStudents)

grades = new RowTable(gradeColumns, listGrades)

dupStudents = new RowTable(studentColumns, dupListStudents)

select = new Select(students, { majorId == 10 })

project = new Project(students, { sname; gradYear })

selectProject = new Project(select, { sname; gradYear })

renamed = new Rename(students, { sid; studentId; sname; studentName; })

sorted = new Sort(dupStudents, { sname; gradYear desc; })

extended = new Extend(students, [ major: mapMajorIds, yearsSinceGraduation: yearsSinceGraduation ])

grouped = new GroupBy(students, { gradYear }, [ totalMajors: { count(majorId) } ])

product = new Product(students, grades)

join = new Join(students, grades, { sid == studentId })

union = new Union(students, dupStudents)

partialGrades = new Select(grades, { studentId > 3 })

semiJoin = new SemiJoin(students, partialGrades, { sid == studentId })

antiJoin = new AntiJoin(students, partialGrades, { sid == studentId })

disjointListGrades =
    [[ 10, 0, 95 ],
     [ 10, 2, 87 ],
     [ 10, 3, 60 ],
     [ 10, 4, 90 ],
     [ 10, 5, 77 ],
     [ 10, 6, 60 ],
     [ 10, 7, 85 ],
     [ 10, 8, 97 ],
     [ 10, 10, 80 ] ]

disjointGrades = new RowTable(gradeColumns, disjointListGrades)

outerJoin = new OuterJoin(students, disjointGrades, { sid == studentId })
