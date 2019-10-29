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

students = new RowTable(studentColumns, listStudents)

grades = new RowTable(gradeColumns, listGrades)

dupStudents = new RowTable(studentColumns, dupListStudents)

project = students.project { sname; gradYear }

selectProject = students.select { majorId == 10 }.project { sname; gradYear }

renamed = students.rename { sid; studentId; sname; studentName; }

sorted = dupStudents.sort { sname; gradYear desc; }

extended = students.extend([ major: mapMajorIds, yearsSinceGraduation: yearsSinceGraduation ])

grouped = students.groupBy({ gradYear }, [ totalMajors: { count(majorId) } ])

product = students.product(grades)

join = students.join(grades) { sid == studentId }

union = students.union(dupStudents)

semiJoin = students.semiJoin(grades.select { studentId > 3 }, { sid == studentId })

antiJoin = students.antiJoin(grades.select { studentId > 3 }, { sid == studentId })

disjointGrades = new RowTable(gradeColumns, disjointListGrades)

outerJoin = students.outerJoin(disjointGrades) { sid == studentId }
